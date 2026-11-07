
package resume_backend.controller;

import org.springframework.web.bind.annotation.*;
import resume_backend.entity.Resume;
import resume_backend.repository.ResumeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin(origins = "http://localhost:5173")
public class ResumeController {

    private final ResumeRepository resumeRepository;

    public ResumeController(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    // Save a new resume
    @PostMapping
    public Resume saveResume(@RequestBody Resume resume) {
        return resumeRepository.save(resume);
    }

    // Get all saved resumes
    @GetMapping
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    // Get one resume using its ID
    @GetMapping("/{id}")
    public Resume getResumeById(@PathVariable Long id) {
        return resumeRepository.findById(id).orElse(null);
    }

    // Update an existing resume using its ID
    @PutMapping("/{id}")
    public Resume updateResume(
            @PathVariable Long id,
            @RequestBody Resume updatedResume) {

        return resumeRepository.findById(id)
                .map(existingResume -> {
                    existingResume.setFirstName(updatedResume.getFirstName());
                    existingResume.setLastName(updatedResume.getLastName());
                    existingResume.setGender(updatedResume.getGender());
                    existingResume.setPhone(updatedResume.getPhone());
                    existingResume.setEmail(updatedResume.getEmail());

                    existingResume.setClass10(updatedResume.getClass10());
                    existingResume.setBoard10(updatedResume.getBoard10());
                    existingResume.setYear10(updatedResume.getYear10());

                    existingResume.setClass12(updatedResume.getClass12());
                    existingResume.setBoard12(updatedResume.getBoard12());
                    existingResume.setYear12(updatedResume.getYear12());

                    existingResume.setDegree(updatedResume.getDegree());
                    existingResume.setCollege(updatedResume.getCollege());
                    existingResume.setGraduationYear(updatedResume.getGraduationYear());

                    existingResume.setSkills(updatedResume.getSkills());
                    existingResume.setExperience(updatedResume.getExperience());
                    existingResume.setProjects(updatedResume.getProjects());
                    existingResume.setAchievements(updatedResume.getAchievements());

                    return resumeRepository.save(existingResume);
                })
                .orElse(null);
    }

    // Delete a resume using its ID
    @DeleteMapping("/{id}")
    public void deleteResume(@PathVariable Long id) {
        resumeRepository.deleteById(id);
    }
}
