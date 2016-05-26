namespace ImageArrangement
{
    partial class Main
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Main));
            this.groupBoxSource = new System.Windows.Forms.GroupBox();
            this.btnBrowseSource = new System.Windows.Forms.Button();
            this.txtSource = new System.Windows.Forms.TextBox();
            this.btnBrowseDestination = new System.Windows.Forms.Button();
            this.txtDestination = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.dateTimePickerTo = new System.Windows.Forms.DateTimePicker();
            this.label2 = new System.Windows.Forms.Label();
            this.dateTimePickerFrom = new System.Windows.Forms.DateTimePicker();
            this.label1 = new System.Windows.Forms.Label();
            this.btnRun = new System.Windows.Forms.Button();
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.groupBoxSource.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBoxSource
            // 
            this.groupBoxSource.Controls.Add(this.btnBrowseSource);
            this.groupBoxSource.Controls.Add(this.txtSource);
            this.groupBoxSource.Location = new System.Drawing.Point(12, 12);
            this.groupBoxSource.Name = "groupBoxSource";
            this.groupBoxSource.Size = new System.Drawing.Size(460, 95);
            this.groupBoxSource.TabIndex = 0;
            this.groupBoxSource.TabStop = false;
            this.groupBoxSource.Text = "Source";
            // 
            // btnBrowseSource
            // 
            this.btnBrowseSource.Location = new System.Drawing.Point(360, 39);
            this.btnBrowseSource.Name = "btnBrowseSource";
            this.btnBrowseSource.Size = new System.Drawing.Size(75, 20);
            this.btnBrowseSource.TabIndex = 1;
            this.btnBrowseSource.Text = "Browse";
            this.btnBrowseSource.UseVisualStyleBackColor = true;
            this.btnBrowseSource.Click += new System.EventHandler(this.btnBrowseSource_Click);
            // 
            // txtSource
            // 
            this.txtSource.Location = new System.Drawing.Point(18, 39);
            this.txtSource.Name = "txtSource";
            this.txtSource.Size = new System.Drawing.Size(331, 20);
            this.txtSource.TabIndex = 0;
            // 
            // btnBrowseDestination
            // 
            this.btnBrowseDestination.Location = new System.Drawing.Point(360, 39);
            this.btnBrowseDestination.Name = "btnBrowseDestination";
            this.btnBrowseDestination.Size = new System.Drawing.Size(75, 20);
            this.btnBrowseDestination.TabIndex = 1;
            this.btnBrowseDestination.Text = "Browse";
            this.btnBrowseDestination.UseVisualStyleBackColor = true;
            this.btnBrowseDestination.Click += new System.EventHandler(this.btnBrowseDestination_Click);
            // 
            // txtDestination
            // 
            this.txtDestination.Enabled = false;
            this.txtDestination.Location = new System.Drawing.Point(18, 39);
            this.txtDestination.Name = "txtDestination";
            this.txtDestination.Size = new System.Drawing.Size(331, 20);
            this.txtDestination.TabIndex = 0;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.btnBrowseDestination);
            this.groupBox1.Controls.Add(this.txtDestination);
            this.groupBox1.Location = new System.Drawing.Point(12, 115);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(460, 100);
            this.groupBox1.TabIndex = 2;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Destination";
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.dateTimePickerTo);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.dateTimePickerFrom);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Location = new System.Drawing.Point(12, 225);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(460, 155);
            this.panel1.TabIndex = 3;
            // 
            // dateTimePickerTo
            // 
            this.dateTimePickerTo.Location = new System.Drawing.Point(70, 99);
            this.dateTimePickerTo.Name = "dateTimePickerTo";
            this.dateTimePickerTo.Size = new System.Drawing.Size(279, 20);
            this.dateTimePickerTo.TabIndex = 4;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(15, 99);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(20, 13);
            this.label2.TabIndex = 3;
            this.label2.Text = "To";
            // 
            // dateTimePickerFrom
            // 
            this.dateTimePickerFrom.Location = new System.Drawing.Point(70, 41);
            this.dateTimePickerFrom.Name = "dateTimePickerFrom";
            this.dateTimePickerFrom.Size = new System.Drawing.Size(279, 20);
            this.dateTimePickerFrom.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(15, 41);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(30, 13);
            this.label1.TabIndex = 1;
            this.label1.Text = "From";
            // 
            // btnRun
            // 
            this.btnRun.Location = new System.Drawing.Point(349, 408);
            this.btnRun.Name = "btnRun";
            this.btnRun.Size = new System.Drawing.Size(75, 23);
            this.btnRun.TabIndex = 4;
            this.btnRun.Text = "Run";
            this.btnRun.UseVisualStyleBackColor = true;
            this.btnRun.Click += new System.EventHandler(this.btnRun_Click);
            // 
            // backgroundWorker1
            // 
            this.backgroundWorker1.WorkerReportsProgress = true;
            this.backgroundWorker1.WorkerSupportsCancellation = true;
            this.backgroundWorker1.DoWork += new System.ComponentModel.DoWorkEventHandler(this.backgroundWorker1_DoWork);
            this.backgroundWorker1.ProgressChanged += new System.ComponentModel.ProgressChangedEventHandler(this.backgroundWorker1_ProgressChanged);
            this.backgroundWorker1.RunWorkerCompleted += new System.ComponentModel.RunWorkerCompletedEventHandler(this.backgroundWorker1_RunWorkerCompleted);
            // 
            // Main
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(484, 461);
            this.Controls.Add(this.btnRun);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.groupBoxSource);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Main";
            this.Text = "Image Arrangement";
            this.groupBoxSource.ResumeLayout(false);
            this.groupBoxSource.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);
            this.CenterToScreen();

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBoxSource;
        private System.Windows.Forms.Button btnBrowseSource;
        private System.Windows.Forms.TextBox txtSource;
        private System.Windows.Forms.Button btnBrowseDestination;
        private System.Windows.Forms.TextBox txtDestination;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button btnRun;
        private System.Windows.Forms.DateTimePicker dateTimePickerTo;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.DateTimePicker dateTimePickerFrom;
        private System.Windows.Forms.Label label1;
        private System.ComponentModel.BackgroundWorker backgroundWorker1;
    }
}

