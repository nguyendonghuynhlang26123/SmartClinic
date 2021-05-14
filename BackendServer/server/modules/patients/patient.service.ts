import { Role, Status } from './../../common/index';
import { AppointmentInterface, PatientInterface } from '../../interfaces';
import { appointmentModel, patientModel, userModel } from '../../models';

export class PatientService {
  async getPatientById(patientId: string) {
    const patient = await patientModel
      .findOne({ _id: patientId })
      .populate('current_appointment');
    if (!patient) {
      throw new Error('Not Found Patient.');
    }
    return patient;
  }

  async getByPhone(phone: string) {
    const patient = await userModel.findOne({
      phone: phone,
      user_type: Role.PATIENT,
    });
    if (!patient) {
      throw new Error('Not Found Patient.');
    }
    return patient;
  }

  async getAllPatient(query?) {
    const perPage = query.per_page ?? 5;
    const page = query.page ?? 1;
    let queryFind = {};
    let selection = {};
    if (query.search)
      queryFind = { patient_name: new RegExp(query.search, 'i') };
    if (query?.select) {
      if (query.select instanceof Array) {
        for (const s of query.select) {
          selection[s] = 1;
        }
      } else selection = { [query.select]: 1 };
    }

    const data = await Promise.all([
      patientModel
        .find(queryFind, null, {
          limit: Number(perPage),
          skip: Number((page - 1) * perPage),
        })
        .sort({ patient_name: 1 }),
      await patientModel.countDocuments(queryFind),
    ]);

    return {
      data: data[0],
      results: data[1],
      page: page,
      totalPage: Math.ceil(data[1] / perPage),
    };
  }

  async createPatient({ patient_name, token }) {
    const patientData: PatientInterface = {
      patient_name: patient_name,
      patient_avatar: `https://ui-avatars.com/api/?name=${encodeURIComponent(
        patient_name
      )}&rounded=true&background=random`,
      token: token,
    };
    return await patientModel.create(patientData);
  }

  async updatePatientById(patientId: string, dataUpdate) {
    console.log(
      'log ~ file: patient.service.ts ~ line 78 ~ PatientService ~ updatePatientById ~ dataUpdate',
      dataUpdate
    );
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
    if (!patient.current_appointment)
      throw new Error('This patient does not have appointment to cancel');
    if (patient.current_appointment.toString() !== appointmentId)
      throw new Error(
        'This patient does not have appointment with category ' + appointmentId
      );

    await appointmentModel.updateOne(
      {
        _id: appointmentId,
      },
      {
        status: Status.CANCELED,
      }
    );

    return await patientModel.updateOne(
      { _id: patientId },
      { current_appointment: null }
    );
  }
}
