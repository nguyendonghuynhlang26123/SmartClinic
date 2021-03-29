import { AppointmentInterface } from '../../interfaces';
import { appointmentModel } from '../../models';

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
    try {
      let filter = {};
      if (query?.date) filter = { ...filter, date: query.date };
      if (query?.service_id) filter = { ...filter, service: query.service_id };

      const appointments = await appointmentModel
        .find(filter)
        .populate('doctor patient service');
      return appointments;
    } catch (error) {
      console.log(error);
      throw new Error('Get All Appointment Error.');
    }
  }

  async createAppointment(data: AppointmentInterface) {
    delete data._id;
    delete data.created_at;
    delete data.updated_at;
    const appointment = await appointmentModel.create(data);
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
