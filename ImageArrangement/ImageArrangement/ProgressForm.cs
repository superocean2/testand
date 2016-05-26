using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ImageArrangement
{
    public partial class ProgressForm : Form
    {
        public ProgressForm()
        {
            InitializeComponent();
            this.CenterToScreen();
        }

        private void ProgressForm_Load(object sender, EventArgs e)
        {
            progressBar1.Maximum = 102;
            progressBar1.Minimum = 0;
        }
        public string Message
        {
            set
            {
                lblStatus.Text = value;
            }
            get
            {
                return lblStatus.Text;
            }
        }
        public int ProgressValue
        {
            set
            {
                progressBar1.Value = value;
            }
            get
            {
                return progressBar1.Value;
            }
        }
        public ProgressBarStyle ProgressStyle
        {
            set
            {
                progressBar1.Style = value;
            }
        }
    }
}
