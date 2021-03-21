import { medicineModel } from "../../models";

export class MedicineService {
  async getMedicineById(medicineId: string) {
    try {
      const medicine = await medicineModel.findOne({ _id: medicineId });
      if (!medicine) {
        throw new Error("Not Found Medicine.");
      }
      return medicine;
    } catch (error) {
      console.log(error);
      throw new Error("Get Medicine Error.");
    }
  }

  async getAllMedicine() {
    try {
      const medicines = await medicineModel.find();
      return medicines;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Medicine Error.");
    }
  }
}
