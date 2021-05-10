import * as express from 'express';
import { connectMongodb } from './connectMongo';
import * as bodyParser from 'body-parser';
import * as session from 'express-session';
import { UserController } from './modules/users/user.controller';
import { AppointmentController } from './modules/appointments/appointment.controller';
import { CategoryController } from './modules/categories/category.controller';
import { DoctorController } from './modules/doctors/doctor.controller';
import { HospitalController } from './modules/hospitals/hospital.controller';
import { MedicineController } from './modules/medicines/medicine.controller';
import { PatientController } from './modules/patients/patient.controller';
import { PrescriptionController } from './modules/prescriptions/prescription.controller';
import { MedicalServiceController } from './modules/medicalServices/medicalServices.controller';
import { AuthController } from './modules/auth/auth.api';
import { TreatmentController } from './modules/treatments/treatment.api';

async function initServer() {
  const app = express();
  const port = process.env.PORT || 3669;

  connectMongodb();

  app.use(
    session({
      resave: false, // don't save session if unmodified
      saveUninitialized: false, // don't create session until something stored
      secret: 'what does the cat says? Meow meow meow',
    })
  );
  //app.use(express.static(__dirname + "/../public"));
  app.use(bodyParser.json());
  app.use(bodyParser.urlencoded({ extended: true }));

  app.use('/users', UserController);
  app.use('/auth', AuthController);
  app.use('/appointments', AppointmentController);
  app.use('/categories', CategoryController);
  app.use('/doctors', DoctorController);
  app.use('/hospitals', HospitalController);
  app.use('/medicines', MedicineController);
  app.use('/patients', PatientController);
  app.use('/prescriptions', PrescriptionController);
  app.use('/treatments', TreatmentController);
  app.use('/medical-services', MedicalServiceController);

  app.get('/', (req, res) => {
    res.send('Hello World!');
  });

  app.get('/ping', (req, res) => {
    res.status(200).json({});
  });

  app.get('*', (req, res, next) => {
    next();
  });

  app.use((req, res, next) => {
    res.json({ status: 'unsuccessful', code: 404, err: 'Not found' });
  });

  app.listen(port, () => {
    console.log(`Server is listening at http://localhost:${port}`);
  });
}

initServer();
