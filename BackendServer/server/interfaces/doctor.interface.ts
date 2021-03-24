import { BaseInterface } from "./base/base.interface";

export class DoctorInterface extends BaseInterface {
  avatar: string;
  bio: string;
  department: string;
  hospital: string;
  token: string;
}
