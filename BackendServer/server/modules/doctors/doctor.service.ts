import { doctorModel } from "../../models";

export class DoctorService {
  async getDoctorById(doctorId: string) {
    try {
      const doctor = await doctorModel.findOne({ _id: doctorId });
      if (!doctor) {
        throw new Error("Not Found Doctor.");
      }
      return doctor;
    } catch (error) {
      console.log(error);
      throw new Error("Get Doctor Error.");
    }
  }

  async getAllDoctor() {
    try {
      const doctors = await doctorModel.find();
      return doctors;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Doctor Error.");
    }
  }
}
