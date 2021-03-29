import { BaseInterface } from './base/base.interface';

export class HospitalInterface extends BaseInterface {
  name: string;
  location: {
    cor_x: number;
    cor_y: number;
  };
  address: string;
  opening_time: string;
  closing_time: string;
  working_days: string;
  contact_number: string;
  thumbnail: string;
}
