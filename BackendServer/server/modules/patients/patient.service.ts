import { PatientInterface } from '../../interfaces/patient.interface';
import { patientModel } from '../../models';
import { UserService } from '../users/user.service';

export class PatientService {
  async getPatientById(patientId: string) {
    try {
      const patient = await patientModel.findOne({ _id: patientId });
      if (!patient) {
        throw new Error('Not Found Patient.');
      }
      return patient;
    } catch (error) {
      console.log(error);
      throw new Error('Get Patient Error.');
    }
  }

  async getAllPatient() {
    try {
      const patients = await patientModel.find();
      return patients;
    } catch (error) {
      console.log(error);
      throw new Error('Get All Patient Error.');
    }
  }

  async createPatient({ patient_name, token }) {
    try {
      const patientData: PatientInterface = {
        patient_name: patient_name,
        patient_avatar: `https://ui-avatars.com/api/?name=${encodeURIComponent(
          patient_name
        )}&rounded=true&background=random`,
        appointment_list: [],
        medical_history: [],
        token: token,
      };
      return await patientModel.create(patientData);
    } catch (error) {
      console.log(error);
      throw new Error('Cannot create patient! ');
    }
  }
}
