import { BaseInterface } from "./base/base.interface";

export class MedicineInterface extends BaseInterface {
  medicine_name: string;
  thumbnail: string;
  price: number;
  description: string;
  direction: string;
  ingredient: string;
  category: string[];
}
