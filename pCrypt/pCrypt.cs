using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Security.Cryptography;

namespace TestApp
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void testBox1_Click(object sender, EventArgs e)
        {
            textBox1.Text = "";
        }

        private void textBox2_Click(object sender, EventArgs e)
        {
            textBox2.Text = "";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            String t1 = textBox1.Text;
            String t2 = textBox2.Text;
            String pw = "";
            for(int i = 0; i < Math.Min(t1.Length, t2.Length); i++)
            {
                if(i % 2 == 0)
                {
                    pw += t1[i];
                }
                else
                {
                    pw += t2[i];
                }
            }
            String s = t2;
            String t = t1;
            if (t1.Length > t2.Length)
            {
                s = t1;
                t = t2;
            }
            for (int i = t.Length; i < s.Length; i++)
            {
                pw += s[i];
            }


            // conversion algorithm
            SHA256 sha256Hash = SHA256.Create();
            byte[] bytes = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(pw));
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < bytes.Length; i++)
            {
                builder.Append(Convert.ToString(((bytes[i] & 0xff) + 0x100), 16)[1]);
            }
            label1.Text = builder.ToString().Substring(0, 12);
            char[] a = label1.Text.ToCharArray();
            for(int i = 0; i < a.Length; i++)
            {
                if((int)a[i] > 96 && (int)a[i] < 121)
                {
                    a[i] = (a[i].ToString().ToUpper()).ToCharArray()[0];
                    break;
                }
            }
            label1.Text = new String(a);
        }
    }
}
