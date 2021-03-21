import { patientModel } from "../../models";

export class PatientService {
  async getPatientById(patientId: string) {
    //: Promise<PatientInterface>
    try {
      const patient = await patientModel.findOne({ _id: patientId });
      if (!patient) {
        throw new Error("Not Found Patient.");
      }
      return patient;
    } catch (error) {
      console.log(error);
      throw new Error("Get Patient Error.");
    }
  }

  async getAllPatient() {
    //: Promise<PatientInterface[]>
    try {
      const patients = await patientModel.find();
      return patients;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Patient Error.");
    }
  }
}
