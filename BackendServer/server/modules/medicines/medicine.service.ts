import { MedicineInterface } from '../../interfaces';
import { medicineModel } from '../../models';

export class MedicineService {
  async getMedicineById(medicineId: string) {
    try {
      const medicine = await medicineModel
        .findOne({ _id: medicineId })
        .populate('category');
      if (!medicine) {
        throw new Error('Not Found Medicine.');
      }
      return medicine;
    } catch (error) {
      console.log(error);
      throw new Error('Get Medicine Error.');
    }
  }

  async getAllMedicine(query?) {
    try {
      if (query?.select && query.select instanceof Array)
        return await medicineModel.find({}, query.select.join(' '));
      const medicines = await medicineModel.find().populate('category');
      return medicines;
    } catch (error) {
      console.log(error);
      throw new Error('Bad request');
    }
  }

  async createMedicine(data: MedicineInterface) {
    delete data._id;
    delete data.created_at;
    delete data.updated_at;
    const medicine = await medicineModel.create(data);
    return medicine;
  }

  async updateMedicineById(medicineId: string, dataUpdate) {
    const medicine = await medicineModel.findOne({ _id: medicineId });
    if (!medicine) throw new Error('Not Found Medicine.');
    const result = await medicineModel.updateOne(
      { _id: medicine._id },
      dataUpdate
    );
    return result;
  }

  async deleteMedicine(medicineId: string) {
    const medicine = await medicineModel.findOne({ _id: medicineId });
    if (!medicine) throw new Error('Not Found Medicine.');
    const result = await medicineModel.deleteOne({ _id: medicine._id }).exec();
    return result;
  }
}
