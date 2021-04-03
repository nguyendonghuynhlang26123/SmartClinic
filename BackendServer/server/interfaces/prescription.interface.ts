import { BaseInterface } from './base/base.interface';

export class PrescriptionInterface extends BaseInterface {
  medicine_list: { medicine: string; note: string };
  doctor: string;
  patient: string;
  symptoms: string;
  diagnosis: string;
  date: number;
  note: string;
}
