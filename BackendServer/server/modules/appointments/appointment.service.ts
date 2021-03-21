import { appointmentModel } from "../../models";

export class AppointmentService {
  async getAppointmentById(appointmentId: string) {
    //: Promise<AppointmentInterface>
    try {
      const appointment = await appointmentModel.findOne({
        _id: appointmentId,
      });
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
    //: Promise<AppointmentInterface[]>
    try {
      const appointments = await appointmentModel.find();
      return appointments;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Appointment Error.");
    }
  }
}
