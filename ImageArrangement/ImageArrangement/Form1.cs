using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ImageArrangement
{
    public partial class Main : Form
    {
        public Main()
        {
            InitializeComponent();
            this.CenterToScreen();
        }
        public List<string> SourceFolders { get; set; }
        public string SearchPattern { get; set; }
        ProgressForm progressDialog;

        private void btnBrowseSource_Click(object sender, EventArgs e)
        {
            SelectSourceDialog dialog = new SelectSourceDialog();
            DialogResult result = dialog.ShowDialog();
            if (result== DialogResult.OK)
            {
                SourceFolders = new List<string>();
                string sourceText = string.Empty;
                for (int i=0;i<dialog.SourcesSelected.Count;i++)
                {
                    SourceFolders.Add(dialog.SourcesSelected[i].Name);
                    if (i!=dialog.SourcesSelected.Count-1)
                    {
                        sourceText += dialog.SourcesSelected[i].Text + ",";
                    }
                    else
                    {
                        sourceText += dialog.SourcesSelected[i].Text;
                    }
                }
                txtSource.Text = sourceText;
                dialog.Close();
            }
        }

        private void btnBrowseDestination_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog dialog = new FolderBrowserDialog();
            if (dialog.ShowDialog()== DialogResult.OK)
            {
                txtDestination.Text = dialog.SelectedPath;
            }
        }

        private void btnRun_Click(object sender, EventArgs e)
        {
            if (!backgroundWorker1.IsBusy)
            {
                backgroundWorker1.RunWorkerAsync();
                progressDialog = new ProgressForm();
                progressDialog.Message = "Searching...";
                progressDialog.ProgressStyle = ProgressBarStyle.Marquee;
                progressDialog.ShowDialog();

            }
           

            
        }

        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {
            BackgroundWorker worker = sender as BackgroundWorker;
            List<FileInfo> files = new List<FileInfo>();
            DateTime fromDate = dateTimePickerFrom.Value;
            DateTime toDate = dateTimePickerTo.Value;
            
            foreach (var folder in SourceFolders)
            {
                if (SearchPattern.Contains("jpg"))
                {
                    files.AddRange(new DirectoryInfo(folder).EnumerateFiles(SearchPattern, SearchOption.AllDirectories).Where(c => c.LastWriteTime >= fromDate).Where(c => c.LastWriteTime < toDate).ToList());
                }
                else
                {
                    string[] q = SearchPattern.Split('|');
                    foreach (var item in q)
                    {
                        files.AddRange(new DirectoryInfo(folder).EnumerateFiles(item, SearchOption.AllDirectories).Where(c => c.LastWriteTime >= fromDate).Where(c => c.LastWriteTime < toDate).ToList());
                    }
                }
            }
            //end search
            Dictionary<string, List<FileInfo>> listSimilar = new Dictionary<string, List<FileInfo>>();
            List<FileInfo> fileOthers = new List<FileInfo>();
            foreach (var file in files)
            {
                if (!fileOthers.Any(c => c.Name == file.Name))
                {
                    fileOthers.Add(file);
                }
                else
                {
                    FileInfo fileExist = fileOthers.Find(c => c.Name == file.Name);
                    if (file.Length != fileExist.Length || file.LastWriteTime != fileExist.LastWriteTime)
                    {
                        fileOthers.Add(fileExist);
                    }
                }
            }

            fileOthers = fileOthers.OrderBy(c => c.LastWriteTime).ToList();
            //end filter
            for (int i = 0; i < fileOthers.Count; i++)
            {
                int percent = (int)Math.Round((decimal)(i*100 / fileOthers.Count), 0);
                worker.ReportProgress(percent);
                if (File.Exists(fileOthers[i].FullName))
                {
                    try
                    {
                        File.Copy(fileOthers[i].FullName, txtDestination.Text + "\\" + (i + 1).ToString() + fileOthers[i].Extension);
                    }
                    catch (Exception ex)
                    {

                    }

                }
            }
        }

        private void backgroundWorker1_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            progressDialog.Message = "Copying... " + e.ProgressPercentage + "%";
            progressDialog.ProgressValue = e.ProgressPercentage;
        }

        private void backgroundWorker1_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            progressDialog.Close();
            MessageBox.Show("Progress done!");
        }

        private void Main_Load(object sender, EventArgs e)
        {
            comboBoxSearchType.Items.Add(new ListItem() { Text = "Image (*.jpg)", Value = "1" });
            comboBoxSearchType.Items.Add(new ListItem() { Text = "Video (*.mp4,*.avi,*.3gp)", Value = "2" });
            comboBoxSearchType.DisplayMember = "Text";
            comboBoxSearchType.ValueMember = "Value";
            comboBoxSearchType.SelectedIndex = 0;
            SearchPattern = "*.jpg";
        }

        private void comboBoxSearchType_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (comboBoxSearchType.SelectedIndex == 0)
            {
                SearchPattern = "*.jpg";
            }
            else
            {
                SearchPattern = "*.mp4|*.avi|*.3gp";
            }
        }
    }
    public class ListItem
    {
        public string Text { get; set; }
        public string Value { get; set; }
    }
}
