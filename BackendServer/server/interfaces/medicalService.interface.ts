import { BaseInterface } from './base/base.interface';

export class MedicalServiceInterface extends BaseInterface {
  service_name: string;
  service_thumbnail?: string;
  service_price: number;
  service_description?: string;
}
