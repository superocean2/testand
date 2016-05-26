namespace ImageArrangement
{
    partial class SelectSourceDialog
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SelectSourceDialog));
            this.panel1 = new System.Windows.Forms.Panel();
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.btnAdd = new System.Windows.Forms.Button();
            this.treeViewSource = new System.Windows.Forms.TreeView();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.listViewSource = new System.Windows.Forms.ListView();
            this.btnRemove = new System.Windows.Forms.Button();
            this.btnOK = new System.Windows.Forms.Button();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).BeginInit();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.splitContainer1);
            this.panel1.Location = new System.Drawing.Point(12, 12);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(760, 491);
            this.panel1.TabIndex = 0;
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.btnAdd);
            this.splitContainer1.Panel1.Controls.Add(this.treeViewSource);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.listViewSource);
            this.splitContainer1.Panel2.Controls.Add(this.btnRemove);
            this.splitContainer1.Size = new System.Drawing.Size(760, 491);
            this.splitContainer1.SplitterDistance = 362;
            this.splitContainer1.TabIndex = 0;
            // 
            // btnAdd
            // 
            this.btnAdd.Location = new System.Drawing.Point(135, 438);
            this.btnAdd.Name = "btnAdd";
            this.btnAdd.Size = new System.Drawing.Size(75, 23);
            this.btnAdd.TabIndex = 2;
            this.btnAdd.Text = "Add";
            this.btnAdd.UseVisualStyleBackColor = true;
            this.btnAdd.Click += new System.EventHandler(this.btnAdd_Click);
            // 
            // treeViewSource
            // 
            this.treeViewSource.ImageIndex = 0;
            this.treeViewSource.ImageList = this.imageList1;
            this.treeViewSource.Location = new System.Drawing.Point(21, 19);
            this.treeViewSource.Name = "treeViewSource";
            this.treeViewSource.SelectedImageIndex = 0;
            this.treeViewSource.Size = new System.Drawing.Size(316, 394);
            this.treeViewSource.StateImageList = this.imageList1;
            this.treeViewSource.TabIndex = 0;
            this.treeViewSource.BeforeExpand += new System.Windows.Forms.TreeViewCancelEventHandler(this.treeViewSource_BeforeExpand);
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "1346238561_folder_classic.png");
            this.imageList1.Images.SetKeyName(1, "1346238604_folder_classic_opened.png");
            this.imageList1.Images.SetKeyName(2, "1346228331_drive.png");
            this.imageList1.Images.SetKeyName(3, "1346228337_drive_cd.png");
            this.imageList1.Images.SetKeyName(4, "1346228356_drive_cd_empty.png");
            this.imageList1.Images.SetKeyName(5, "1346228364_drive_disk.png");
            this.imageList1.Images.SetKeyName(6, "1346228591_drive_network.png");
            this.imageList1.Images.SetKeyName(7, "1346228618_drive_link.png");
            this.imageList1.Images.SetKeyName(8, "1346228623_drive_error.png");
            this.imageList1.Images.SetKeyName(9, "1346228633_drive_go.png");
            this.imageList1.Images.SetKeyName(10, "1346228636_drive_delete.png");
            this.imageList1.Images.SetKeyName(11, "1346228639_drive_burn.png");
            this.imageList1.Images.SetKeyName(12, "1346238642_folder_classic_locked.png");
            // 
            // listViewSource
            // 
            this.listViewSource.LargeImageList = this.imageList1;
            this.listViewSource.Location = new System.Drawing.Point(3, 19);
            this.listViewSource.Name = "listViewSource";
            this.listViewSource.Size = new System.Drawing.Size(388, 394);
            this.listViewSource.SmallImageList = this.imageList1;
            this.listViewSource.StateImageList = this.imageList1;
            this.listViewSource.TabIndex = 4;
            this.listViewSource.UseCompatibleStateImageBehavior = false;
            this.listViewSource.View = System.Windows.Forms.View.List;
            // 
            // btnRemove
            // 
            this.btnRemove.Location = new System.Drawing.Point(162, 438);
            this.btnRemove.Name = "btnRemove";
            this.btnRemove.Size = new System.Drawing.Size(75, 23);
            this.btnRemove.TabIndex = 3;
            this.btnRemove.Text = "Remove";
            this.btnRemove.UseVisualStyleBackColor = true;
            this.btnRemove.Click += new System.EventHandler(this.btnRemove_Click);
            // 
            // btnOK
            // 
            this.btnOK.Location = new System.Drawing.Point(340, 526);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(75, 23);
            this.btnOK.TabIndex = 1;
            this.btnOK.Text = "OK";
            this.btnOK.UseVisualStyleBackColor = true;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // SelectSourceDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(784, 561);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.panel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "SelectSourceDialog";
            this.ShowInTaskbar = false;
            this.Text = "Select Drive and Folder";
            this.Load += new System.EventHandler(this.SelectSourceDialog_Load);
            this.panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).EndInit();
            this.splitContainer1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.CenterToScreen();

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button btnOK;
        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.Button btnAdd;
        private System.Windows.Forms.TreeView treeViewSource;
        private System.Windows.Forms.Button btnRemove;
        private System.Windows.Forms.ImageList imageList1;
        private System.Windows.Forms.ListView listViewSource;
    }
}