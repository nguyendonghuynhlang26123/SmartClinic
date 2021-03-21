import { doctorModel } from "../../models";

export class DoctorService {
  async getDoctorById(doctorId: string) {
    //: Promise<DoctorInterface>
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
    //: Promise<DoctorInterface[]>
    try {
      const doctors = await doctorModel.find();
      return doctors;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Doctor Error.");
    }
  }
}
