import { BaseInterface } from "./base/base.interface";

export class PrescriptionInterface extends BaseInterface {
  medicine_list: { medicine: string; note: string };
  reexam_date: number;
  doctor: string;
  patient: string;
  date: number;
  note: string;
}
