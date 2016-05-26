using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Security;
using System.Security.Permissions;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ImageArrangement
{
    public partial class SelectSourceDialog : Form
    {
        public SelectSourceDialog()
        {
            InitializeComponent();
        }
        public List<ListViewItem> SourcesSelected { get; set; }
        private void SelectSourceDialog_Load(object sender, EventArgs e)
        {
            foreach (string drive in Environment.GetLogicalDrives())
            {
                DriveInfo driveInfo = new DriveInfo(drive);
                int driveImage;

                switch (driveInfo.DriveType)    //set the drive's icon
                {
                    case DriveType.CDRom:
                        driveImage = 3;
                        break;
                    case DriveType.Network:
                        driveImage = 6;
                        break;
                    case DriveType.NoRootDirectory:
                        driveImage = 8;
                        break;
                    case DriveType.Unknown:
                        driveImage = 8;
                        break;
                    default:
                        driveImage = 2;
                        break;
                }

                TreeNode node = new TreeNode("Drive "+drive.Substring(0, 1), driveImage, driveImage);
                node.Tag = drive;
                if (driveInfo.IsReady)
                {
                    node.Nodes.Add("...");
                }

                treeViewSource.Nodes.Add(node);

            }
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            TreeNode selectedNode = treeViewSource.SelectedNode;
            ListViewItem Additem = new ListViewItem() { Text = selectedNode.Text, Name = selectedNode.Tag.ToString(), ImageIndex = selectedNode.ImageIndex };
            bool isAdded = false;
            foreach (ListViewItem item in listViewSource.Items)
            {
                if (item.Name==Additem.Name)
                {
                    isAdded = true;
                    return;
                }
            }


            if (!isAdded && !selectedNode.Text.Contains("Drive"))
            {
                listViewSource.Items.Add(Additem);
            }

        }

        private void btnRemove_Click(object sender, EventArgs e)
        {
            foreach (ListViewItem item in listViewSource.SelectedItems)
            {
                listViewSource.Items.Remove(item);
            }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            SourcesSelected = new List<ListViewItem>();
            foreach (ListViewItem item in listViewSource.Items)
            {
                SourcesSelected.Add(item);
            }
            this.DialogResult = DialogResult.OK;
        }


        private void treeViewSource_BeforeExpand(object sender, TreeViewCancelEventArgs e)
        {
            if (e.Node.Nodes.Count > 0)
            {
                if (e.Node.Nodes[0].Text == "..." && e.Node.Nodes[0].Tag == null)
                {
                    e.Node.Nodes.Clear();

                    //get the list of sub direcotires
                    string[] dirs = Directory.GetDirectories(e.Node.Tag.ToString());

                    foreach (string dir in dirs)
                    {
                        DirectoryInfo di = new DirectoryInfo(dir);
                        if ((di.Attributes & FileAttributes.System)!= FileAttributes.System)
                        {
                            TreeNode node = new TreeNode(di.Name, 0, 1);

                            try
                            {
                                //keep the directory's full path in the tag for use later
                                node.Tag = dir;

                                //if the directory has sub directories add the place holder
                                if (di.GetDirectories().Count() > 0)
                                    node.Nodes.Add(null, "...", 0, 0);
                            }
                            catch (UnauthorizedAccessException)
                            {
                                //display a locked folder icon
                                node.ImageIndex = 12;
                                node.SelectedImageIndex = 12;
                            }
                            catch (Exception ex)
                            {
                                MessageBox.Show(ex.Message, "DirectoryLister",
                                    MessageBoxButtons.OK, MessageBoxIcon.Error);
                            }
                            finally
                            {
                                e.Node.Nodes.Add(node);
                            }
                        }
                        
                    }
                }
            }
        }
    }
}
