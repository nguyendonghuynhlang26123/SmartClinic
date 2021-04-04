import { AppointmentInterface, PatientInterface } from '../../interfaces';
import {
  doctorModel,
  patientModel,
  medicalServiceModel,
  appointmentModel,
} from '../../models';
import { UserService } from '../users/user.service';

export class PatientService {
  async getPatientById(patientId: string) {
    try {
      const patient = await patientModel
        .findOne({ _id: patientId })
        .populate('appointment_list');
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
      const patients = await patientModel.find().populate('appointment_list');
      return patients;
    } catch (error) {
      console.log(error);
      throw new Error('Get All Patient Error.');
    }
  }

  async createPatient({ patient_name, token }) {
    const patientData: PatientInterface = {
      patient_name: patient_name,
      patient_avatar: `https://ui-avatars.com/api/?name=${encodeURIComponent(
        patient_name
      )}&rounded=true&background=random`,
      medical_history: [],
      token: token,
    };
    return await patientModel.create(patientData);
  }

  async updatePatientById(patientId: string, dataUpdate) {
    if (dataUpdate?.medical_history) delete dataUpdate.medical_history;

    const patient = await patientModel.findOne({ _id: patientId });
    if (!patient) throw new Error('Not Found Patient.');
    const result = await patientModel.updateOne(
      { _id: patient._id },
      dataUpdate
    );
    return result;
  }

  async deletePatient(patientId: string) {
    const patient = await patientModel.findOne({ _id: patientId });
    if (!patient) throw new Error('Not Found Patient.');
    const result = await patientModel.deleteOne({ _id: patient._id }).exec();
    return result;
  }
}
