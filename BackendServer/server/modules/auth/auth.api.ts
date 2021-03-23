import * as express from "express";
import { AuthService } from "./auth.service";
const router = express.Router();

const authService: AuthService = new AuthService();

// router.get("/login", (req, res) => {
//   if (req.session.auth) res.redirect("/docs");
//   else
//     res.render("pages/loginPage", {
//       message: req.session.error,
//     });
// });

router.post("/login", (req, res) => {
  authService.authenticate(req.body.phone, req.body.password, (err, user) => {
    if (user) {
      req.session.regenerate(() => {
        req.session.auth = true;
        req.session.user_id = user._id;
        req.session.user_type = user.user_type;
        res.json({ status: "successful" });
      });
    } else {
      //req.session.error = `${err}. Authentication failed, please check your username and password.`;
      res.json({
        status: "unsuccessful",
        err: `${err}. Authentication failed, please check your username and password.`,
      });
    }
  });
});

// router.get("/register", (req, res) => {
//   res.render("signing/register", {
//     link: "/style/css/signing.css",
//     message: req.session.error,
//   });
// });

router.get("/logout", (req, res) => {
  req.session.destroy(() => {
    res.redirect("/login");
  });
});

export const AuthController = router;