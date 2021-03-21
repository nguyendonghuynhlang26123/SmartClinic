import { categoryModel } from "../../models";

export class CategoryService {
  async getCategoryById(categoryId: string) {
    //: Promise<CategoryInterface>
    try {
      const category = await categoryModel.findOne({ _id: categoryId });
      if (!category) {
        throw new Error("Not Found Category.");
      }
      return category;
    } catch (error) {
      console.log(error);
      throw new Error("Get Category Error.");
    }
  }

  async getAllCategory() {
    //: Promise<CategoryInterface[]>
    try {
      const categories = await categoryModel.find();
      return categories;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Category Error.");
    }
  }
}
