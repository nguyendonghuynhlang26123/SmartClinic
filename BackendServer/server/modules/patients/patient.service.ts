import { AppointmentInterface, PatientInterface } from '../../interfaces';
import {
  doctorModel,
  patientModel,
  medicalServiceModel,
  appointmentModel,
} from '../../models';

export class PatientService {
  async getPatientById(patientId: string) {
    try {
      const patient = await patientModel
        .findOne({ _id: patientId })
        .populate('current_appointment');
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

  async cancelAppointment(patientId: string, appointmentId: string) {
    if (!appointmentId || !patientId)
      throw new Error('Bad Request! PatientId or AppointmentId is undefined');

    const patient: any = (
      await patientModel.findOne({ _id: patientId })
    ).toObject();
    if (!patient) throw new Error('Not Found Patient.');
    if (patient.current_appointment.toString() !== appointmentId)
      throw new Error(
        'This patient does not have appointment with category ' + appointmentId
      );

    const appointment = await appointmentModel.findOne({ _id: appointmentId });
    if (!appointment) throw new Error('Not Found Appointment.');

    await patientModel.updateOne(
      { _id: patientId },
      { current_appointment: null }
    );
    return await appointmentModel.updateOne(
      { _id: patientId },
      { status: 'CANCELED' }
    );
  }

  async getMedicalHistory(patientId: string) {
    if (!patientId) throw new Error('Bad Request! PatientId is undefined');

    const data: any = await patientModel
      .findOne({ _id: patientId }, 'medical_history')
      .populate({
        path: 'medical_history',
        populate: [
          {
            path: 'appointment',
            model: 'appointments',
            populate: [
              {
                path: 'service',
                select: 'service_name',
              },
              {
                path: 'doctor',
                select: 'name',
              },
            ],
          },
          {
            path: 'prescription',
            model: 'prescriptions',
            populate: {
              path: 'medicine_list.medicine',
              model: 'medicines',
              select: 'medicine_name',
            },
          },
        ],
      });

    if (!data) {
      throw new Error('Not Found Patient.');
    }
    return data.toObject().medical_history;
  }
}
