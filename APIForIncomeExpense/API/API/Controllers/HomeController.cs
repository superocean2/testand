using API.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using API.Models;

namespace API.Controllers
{
    public class HomeController : Controller
    {
        DatabaseContext db = new Models.DatabaseContext();
        // GET: Home
        public string GetData()
        {
            //User user = new User();
            //user.id = 1;
            //user.username = "test";
            //user.password = "123456";
            //return Json(user,JsonRequestBehavior.AllowGet);
            String username = "nghia";
            String password = "123456";
            if (!string.IsNullOrWhiteSpace(username) && !string.IsNullOrWhiteSpace(password))
            {
                Models.User user = new Models.User { username = username, password = password };
                db.Users.Add(user);
                db.SaveChanges();
            }
            return "test";
        }
        //POST: Register
        [HttpPost]
        public void Register()
        {
            String username = Request.Form["username"].ToString().Trim();
            String password = Request.Form["password"].ToString().Trim();
            if (!string.IsNullOrWhiteSpace(username) && !string.IsNullOrWhiteSpace(password))
            {
                if (db.Users.Any(c => c.username == username))
                {
                    throw new Exception();
                }
                else
                {
                    Models.User user = new Models.User { username = username, password = password };
                    db.Users.Add(user);
                    db.SaveChanges();
                }

            }
        }

        //GET: Login
        public string Login()
        {
            String username = "";
            String password = "";
            if (Request.QueryString["username"] != null && Request.QueryString["password"] != null)
            {
                username = Request.QueryString["username"].ToString();
                password = Request.QueryString["password"].ToString();
            }

            if (!string.IsNullOrWhiteSpace(username) && !string.IsNullOrWhiteSpace(password))
            {
                User user = db.Users.FirstOrDefault(c => c.username == username);
                if (user != null)
                {
                    if (user.password == password)
                    {
                        return "ok";
                    }
                }
            }
            return "error";
        }

    }
}