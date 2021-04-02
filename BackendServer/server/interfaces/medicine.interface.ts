import { BaseInterface } from './base/base.interface';

export class MedicineInterface extends BaseInterface {
  medicine_name: string;
  thumbnail: string;
  price: number;
  unit?: string;
  description?: string;
  user_manual?: string;
  ingredient?: string;
  volume?: string;
  brand?: string;
  from?: string;
  preservation?: string;
  category: string;
}
