import { PatientInterface } from '../../interfaces';
import { patientModel } from '../../models';
import { UserService } from '../users/user.service';

export class PatientService {
  async getPatientById(patientId: string) {
    try {
      const patient = await patientModel.findOne({ _id: patientId }).populate("appointment_list");
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
      const patients = await patientModel.find().populate("appointment_list");
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

  async updatePatient(id: String, data: PatientInterface) {
    const patient = patientModel.findOne({ _id: id });
    if (!patient) throw new Error('Not found user');
  }
}
