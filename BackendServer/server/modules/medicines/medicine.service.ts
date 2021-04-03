import { MedicineInterface } from '../../interfaces';
import { categoryModel, medicineModel } from '../../models';

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

  async getAllMedicine(query) {
    let selection = {};
    let filter = {};
    if (query?.select) {
      if (query.select instanceof Array) {
        for (const property of query.select) {
          selection[property] = 1;
        }
      } else selection = { [query.select]: 1 };
    }
    if (query?.category) filter['category'] = query.category;
    const medicines = await medicineModel.find(
      {
        medicine_name: new RegExp(query.search, 'i'),
        ...filter,
      },
      selection,
      {
        limit: Number(query.limit),
      }
    );
    return medicines;
  }

  async getMedicinesByCategory(category_id, query?) {
    const category = categoryModel.findOne({ _id: category_id });
    if (!category) throw new Error('Category not found');

    let selection = {};
    if (query?.select) {
      if (query.select instanceof Array) {
        for (const property of query.select) {
          selection[property] = 1;
        }
      } else selection = { [query.select]: 1 };
    }

    const medicines = await medicineModel.find(
      {
        category: category_id,
      },
      selection,
      {
        limit: Number(query.limit),
      }
    );
    return medicines;
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
