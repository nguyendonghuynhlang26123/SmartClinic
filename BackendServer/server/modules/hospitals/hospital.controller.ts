import * as express from "express";
import { HospitalService } from "./hospital.service";
const router = express.Router();

const hospitalService: HospitalService = new HospitalService();

router.get("/", async (req, res) => {
  const hospitals = await hospitalService.getAllHospital();
  res.json(hospitals);
});

router.get("/:hospital_id", async (req, res) => {
  const hospital = await hospitalService.getHospitalById(
    req.params.hospital_id
  );
  res.json(hospital);
});

router.post("/", async (req, res) => {
  const hospital = await hospitalService.createHospital(req.body);
  res.json({ _id: hospital.id });
});

export const HospitalController = router;
