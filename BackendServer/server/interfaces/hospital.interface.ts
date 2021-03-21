import { BaseInterface } from "./base/base.interface";

export class HospitalInterface extends BaseInterface {
  name: string;
  location: "cor_x" | "cor_y";
}
