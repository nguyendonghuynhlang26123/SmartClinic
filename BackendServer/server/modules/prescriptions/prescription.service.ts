import { prescriptionModel } from "../../models";

export class PrescriptionService {
  async getPrescriptionById(prescriptionId: string) {
    //: Promise<PrescriptionInterface>
    try {
      const prescription = await prescriptionModel.findOne({ _id: prescriptionId });
      if (!prescription) {
        throw new Error("Not Found Prescription.");
      }
      return prescription;
    } catch (error) {
      console.log(error);
      throw new Error("Get Prescription Error.");
    }
  }

  async getAllPrescription() {
    //: Promise<PrescriptionInterface[]>
    try {
      const prescriptions = await prescriptionModel.find();
      return prescriptions;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Prescription Error.");
    }
  }
}
