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
        req.session.user_type = user.user_type;
        res.json({ status: 'successful' });
      });
    } else {
      //req.session.error = `${err}. Authentication failed, please check your username and password.`;
      res.status(400).json({ 'message': 'Invalid credentials' });
    }
  });
});

router.post('/patients/register', async (req, res) => {
  const userSevice = new UserService();
  const patientService = new PatientService();
  try {
    let newPatient = await patientService.createPatient(req.body);
    let user = await userSevice.createUser({
      ...req.body,
      user_type: 'PATIENT',
      user_infor: newPatient._id,
    });
    res.json(user);
  } catch (err) {
    res.status(400).json({ 'message': err.message });
  }
});

export const AuthController = router;
