import * as express from "express";
import { MedicineService } from "./medicine.service";
const router = express.Router();

const medicineService: MedicineService = new MedicineService();

router.get("/", async (req, res) => {
  const medicines = await medicineService.getAllMedicine();
  res.json(medicines);
});

router.get("/:medicine_id", async (req, res) => {
  const medicine = await medicineService.getMedicineById(
    req.params.medicine_id
  );
  res.json(medicine);
});
