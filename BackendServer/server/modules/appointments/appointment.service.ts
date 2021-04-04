import { AppointmentInterface, PrescriptionDetails } from '../../interfaces';
import {
  appointmentModel,
  patientModel,
  doctorModel,
  medicalServiceModel,
} from '../../models';

export class AppointmentService {
  async getAppointmentById(appointmentId: string) {
    try {
      const appointment = await appointmentModel
        .findOne({
          _id: appointmentId,
        })
        .populate('doctor patient');
      if (!appointment) {
        throw new Error('Not Found Appointment.');
      }
      return appointment;
    } catch (error) {
      console.log(error);
      throw new Error('Get Appointment Error.');
    }
  }

  async getAllAppointment(query?) {
    let filter = {};
    let selection = {};
    if (query?.date) filter = { ...filter, date: query.date };
    if (query?.is_expired) filter = { ...filter, is_expired: query.is_expired };
    if (query?.service_id) filter = { ...filter, service: query.service_id };
    if (query?.patient_id) filter = { ...filter, patient: query.patient_id };
    if (query?.doctor_id) filter = { ...filter, patient: query.doctor_id };
    if (query?.select) {
      if (query.select instanceof Array) {
        for (const s of query.select) {
          selection[s] = 1;
        }
      } else selection = { [query.select]: 1 };
    }

    const appointments = await appointmentModel.find(filter, selection, {
      limit: Number(query?.limit),
    });
    return appointments;
  }

  async createAppointment(data: AppointmentInterface) {
    delete data._id;
    delete data.created_at;
    delete data.updated_at;
    if (!data.time || !data.date)
      throw new Error('Bad request! Time & date is required');

    const patient = await patientModel.findOne({ _id: data.patient });
    if (!patient) throw new Error('Not Found Patient.');

    const doctor = await doctorModel.findOne({ _id: data.doctor });
    if (!doctor) throw new Error('Not found doctor');

    const service = await medicalServiceModel.findOne({ _id: data.service });
    if (!service) throw new Error('Not found Medical Service');

    const checkAppointment = await appointmentModel.findOne({
      doctor: data.doctor,
      service: data.service,
      time: data.time,
      date: data.date,
    });
    if (checkAppointment)
      throw new Error(
        'This doctor has another appointment at this time! Please try again'
      );

    const appointment = await appointmentModel.create(data);
    if (!appointment)
      throw new Error('Error found when creating an appointment');
    const appointmentId = (await appointment.toObject())._id;
    const updatedData = {
      appointment: appointmentId,
      prescription: null,
    };

    await patientModel.updateOne(
      { _id: data.patient },
      {
        $push: {
          medical_history: updatedData,
        },
      }
    );

    return appointment;
  }

  async updateAppointmentById(appointmentId: string, dataUpdate) {
    const appointment = await appointmentModel.findOne({ _id: appointmentId });
    if (!appointment) throw new Error('Not Found Appointment.');
    const result = await appointmentModel.updateOne(
      { _id: appointment._id },
      dataUpdate
    );
    return result;
  }

  async deleteAppointment(appointmentId: string) {
    const appointment = await appointmentModel.findOne({ _id: appointmentId });
    if (!appointment) throw new Error('Not Found Appointment.');
    const result = await appointmentModel
      .deleteOne({ _id: appointment._id })
      .exec();
    return result;
  }
}
