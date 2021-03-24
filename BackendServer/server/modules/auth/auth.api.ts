import * as express from 'express';
import { UserService } from './../users/user.service';
import { AuthService } from './auth.service';
import { PatientService } from '../patients/patient.service';
const router = express.Router();

const authService: AuthService = new AuthService();

router.get('/logout', (req, res) => {
  req.session.destroy(() => {
    //res.redirect('/login');
  });
});

router.post('/patients/login', (req, res) => {
  authService.authenticate(req.body.phone, req.body.password, (err, user) => {
    if (user) {
      req.session.regenerate(() => {
        req.session.auth = true;
        req.session.user_id = user._id;
        //req.session.user_type = user.user_type;
        delete user.password;
        res.json(user);
      });
    } else {
      console.log(err);
      //req.session.error = `${err}. Authentication failed, please check your username and password.`;
      res.status(400).json({ 'message': err.message });
    }
  });
});

router.post('/patients/register', async (req, res) => {
  const userService = new UserService();
  const patientService = new PatientService();
  try {
    const isExisted = await userService.checkExisted(req.body.phone);
    if (isExisted) throw new Error('Registered phone number');
    let newPatient = await patientService.createPatient(req.body);
    let user = (
      await userService.createUser({
        ...req.body,
        user_type: 'PATIENT',
        user_infor: newPatient._id,
      })
    ).toObject();
    res.json({ ...user, user_infor: newPatient });
  } catch (err) {
    res.status(400).json({ 'message': err.message });
  }
});

export const AuthController = router;
