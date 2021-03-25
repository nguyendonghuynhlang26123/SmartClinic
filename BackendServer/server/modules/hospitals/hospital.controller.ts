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
  try {
    const hospital = await hospitalService.createHospital(req.body);
    res.json(hospital);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

router.put("/:hospital_id", async (req, res) => {
  try {
    const result = await hospitalService.updateHospitalById(
      req.params.hospital_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.delete("/:hospital_id", async (req, res) => {
  try {
    const result = await hospitalService.deleteHospital(
      req.params.hospital_id
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const HospitalController = router;
