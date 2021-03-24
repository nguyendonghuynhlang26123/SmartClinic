import { AppointmentInterface } from "../../interfaces";
import { appointmentModel } from "../../models";

export class AppointmentService {
  async getAppointmentById(appointmentId: string) {
    try {
      const appointment = await appointmentModel
        .findOne({
          _id: appointmentId,
        })
        .populate("doctor patient");
      if (!appointment) {
        throw new Error("Not Found Appointment.");
      }
      return appointment;
    } catch (error) {
      console.log(error);
      throw new Error("Get Appointment Error.");
    }
  }

  async getAllAppointment() {
    try {
      const appointments = await appointmentModel
        .find()
        .populate("doctor patient");
      return appointments;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Appointment Error.");
    }
  }

  async createAppointment(data: AppointmentInterface) {
    try {
      delete data._id;
      delete data.created_at;
      delete data.updated_at;
      const appointment = await appointmentModel.create(data);
      return appointment;
    } catch (error) {
      throw new Error("Create Appointment Error.");
    }
  }
}
