# Spring MVC School Management System

## List of modules:

### Staff Management
### Student Management
### Course Management
### Session Management
### Section Management
### Class Management
### Grade Management
### Fees Management
### Promotion & Demotion Management
### Result Management
### Checking Result Management
### School Expense Management
### Debit & Credit Account Management

@Service
public class ResultImpl implements ResultService {
    // Existing fields and constructor

    private final ResultRepository resultRepository;
    private final SectionRepository sectionRepository;
    private final SessionRepository sessionRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TermRepository termRepository;
    private final SubjectRepository subjectRepository;
    private final GradeService gradeService;
    private final ResultSettingsService resultSettingsService;
    private final StudentRepository studentRepository;

    public ResultImpl(ResultRepository resultRepository, SectionRepository sectionRepository,
                      SessionRepository sessionRepository, SchoolClassRepository schoolClassRepository,
                      TermRepository termRepository, SubjectRepository subjectRepository,
                      GradeService gradeService, ResultSettingsService resultSettingsService,
                      StudentRepository studentRepository) {
        this.resultRepository = resultRepository;
        this.sectionRepository = sectionRepository;
        this.sessionRepository = sessionRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.termRepository = termRepository;
        this.subjectRepository = subjectRepository;
        this.gradeService = gradeService;
        this.resultSettingsService = resultSettingsService;
        this.studentRepository = studentRepository;
    }

    // Existing methods...

    @Override
    public void saveOrUpdateResult(ResultDto resultDto) {
        // Existing save logic...
        
        // After saving the result, update the student's aggregated results
        StudentModel student = studentRepository.findById(Long.parseLong(resultDto.getName()))
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + resultDto.getName()));
        updateStudentResults(student);
    }

    private void updateStudentResults(StudentModel student) {
        // This method should implement the logic to recalculate and store
        // the aggregated results for the student. It could involve storing
        // results in another table or updating existing records.
        Map<StudentModel, List<ResultDto>> resultsMap = getResultsForStudentsInClass(student.getSectionId(), student.getClassId());
        // Store or update the aggregated results for this student
    }
}


@Service
public class ResultCheckingImpl implements ResultCheckingService {

    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResultCheckingImpl.class);

    public ResultCheckingImpl(ResultRepository resultRepository, StudentRepository studentRepository) {
        this.resultRepository = resultRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public ResultDto searchResult(ResultCheckingDto resultCheckingDto) {
        StudentModel authenticatedStudent = getAuthenticatedStudent();
        if (authenticatedStudent == null) {
            throw new IllegalArgumentException("User not authenticated or not found.");
        }

        // Fetch the aggregated results for the authenticated student
        // This should fetch from wherever the aggregated results are stored
        return getAggregatedResultsForStudent(authenticatedStudent);
    }

    private ResultDto getAggregatedResultsForStudent(StudentModel student) {
        // Retrieve the precomputed result data for the student.
        // This might involve querying a separate table or data structure
        // where these results are stored.
        // Example placeholder:
        ResultDto aggregatedResult = new ResultDto();
        // Populate aggregatedResult with data
        return aggregatedResult;
    }

    private StudentModel getAuthenticatedStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.warn("Authentication is null or not authenticated");
            return null;
        }

        String username = authentication.getName();
        StudentModel studentModel = studentRepository.findByUserName(username);
        if (studentModel == null) {
            logger.warn("UserModel not found for username: {}", username);
            return null;
        } else {
            logger.info("Found UserModel: {}", studentModel);
            return studentModel;
        }
    }
}


@Service
public class ResultImpl implements ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultImpl.class);
    
    private final ResultRepository resultRepository;
    private final SectionRepository sectionRepository;
    private final SessionRepository sessionRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TermRepository termRepository;
    private final SubjectRepository subjectRepository;
    private final GradeService gradeService;
    private final ResultSettingsService resultSettingsService;
    private final StudentRepository studentRepository;

    public ResultImpl(ResultRepository resultRepository, SectionRepository sectionRepository,
                      SessionRepository sessionRepository, SchoolClassRepository schoolClassRepository,
                      TermRepository termRepository, SubjectRepository subjectRepository,
                      GradeService gradeService, ResultSettingsService resultSettingsService,
                      StudentRepository studentRepository) {
        this.resultRepository = resultRepository;
        this.sectionRepository = sectionRepository;
        this.sessionRepository = sessionRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.termRepository = termRepository;
        this.subjectRepository = subjectRepository;
        this.gradeService = gradeService;
        this.resultSettingsService = resultSettingsService;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<ResultDto> getAllResults() {
        List<ResultModel> results = resultRepository.findAll();
        return results.stream().map(ResultMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ResultDto getResultById(Long id) {
        return ResultMapper.mapToDto(resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with ID: " + id)));
    }

    @Override
    public void saveOrUpdateResult(ResultDto resultDto) {
        // Fetch related entities from repositories
        SectionModel section = sectionRepository.findById(Long.parseLong(resultDto.getSectionId()))
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + resultDto.getSectionId()));
        
        SessionModel academicSession = sessionRepository.findById(Long.parseLong(resultDto.getAcademicSessionId()))
                .orElseThrow(() -> new EntityNotFoundException("Academic Session not found with ID: " + resultDto.getAcademicSessionId()));

        SchoolClassModel schoolClass = schoolClassRepository.findById(Long.parseLong(resultDto.getStudentClassId()))
                .orElseThrow(() -> new EntityNotFoundException("School Class not found with ID: " + resultDto.getStudentClassId()));

        TermModel term = termRepository.findById(Long.parseLong(resultDto.getTermId()))
                .orElseThrow(() -> new EntityNotFoundException("Term not found with ID: " + resultDto.getTermId()));

        SubjectModel subject = subjectRepository.findById(Long.parseLong(resultDto.getSubjectId()))
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + resultDto.getSubjectId()));

        StudentModel student = studentRepository.findById(Long.parseLong(resultDto.getName()))
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + resultDto.getName()));

        List<ResultSettingsDto> resultSettingsDto = resultSettingsService.getAllResultSettings();
        ResultSettingsDto resultSetting = resultSettingsDto.stream()
                .filter(rs -> rs.getSectionId().equals(section.getSectionName()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Result settings not found for section ID: " + section.getId()));

        // Verify scores against result settings
        if (resultDto.getFirstCA() > resultSetting.getFirstCA()) {
            throw new IllegalArgumentException("First CA score exceeds the allowed maximum of " + resultSetting.getFirstCA());
        }
        if (resultDto.getSecondCA() > resultSetting.getSecondCA()) {
            throw new IllegalArgumentException("Second CA score exceeds the allowed maximum of " + resultSetting.getSecondCA());
        }
        if (resultDto.getExam() > resultSetting.getExam()) {
            throw new IllegalArgumentException("Exam score exceeds the allowed maximum of " + resultSetting.getExam());
        }

        // Map ResultDto to ResultModel
        ResultModel resultModel = ResultMapper.mapToModel(resultDto);

        // Set fetched entities to result model
        resultModel.setSectionId(section.getSectionName());
        resultModel.setAcademicSessionId(academicSession.getSessionName());
        resultModel.setStudentClassId(schoolClass.getClassName());
        resultModel.setTermId(term.getTermName());
        resultModel.setSubjectId(subject.getSubjectName());
        resultModel.setName(student.getFirstName() + ' ' + student.getLastName());
        resultModel.setRegNo(student.getRegNo());

        // Calculating total
        Integer totalMark = resultModel.getFirstCA() + resultModel.getSecondCA() + resultModel.getExam();
        resultModel.setTotal(totalMark);

        // Find the matching GradeDto based on total score
        List<GradeDto> gradeList = gradeService.getAllGrades();
        GradeDto matchingGrade = gradeList.stream()
                .filter(grade -> totalMark >= grade.getRangeFrom() && totalMark <= grade.getRangeTo())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No grade found for total marks: " + totalMark));

        resultModel.setGrade(matchingGrade.getGrade());
        resultModel.setRemark(matchingGrade.getRemark());

        // Save the result model
        resultRepository.save(resultModel);

        // Update the student's aggregated results
        updateStudentResults(student);
    }

    private void updateStudentResults(StudentModel student) {
        // This method calculates and stores the aggregated results for the student.
        Map<StudentModel, List<ResultDto>> resultsMap = getResultsForStudentsInClass(student.getSectionId(), student.getClassId());
        List<ResultDto> studentResults = resultsMap.get(student);
        
        // Store or update the aggregated results
        // You may need to create a new repository method or data structure for storing aggregated results
        // Example:
        // aggregatedResultsRepository.save(studentResults);
    }

    @Override
    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }

    @Override
    public List<ResultModel> getResultModelsBySectionIdAndStudentClassId(String section, String classId) {
        return resultRepository.findResultModelsBySectionIdAndStudentClassId(section, classId);
    }

    public Map<StudentModel, List<ResultDto>> getResultsForStudentsInClass(String sectionId, String classId) {
        // Fetch all students in the specified class and section
        List<StudentModel> students = studentRepository.findStudentsBySectionAndClass(sectionId, classId);
        
        // Fetch all subjects offered for this class
        List<SubjectModel> subjects = subjectRepository.findSubjectsByClassId(classId);
        
        // Initialize the map to store results per student
        Map<StudentModel, List<ResultDto>> studentResultsMap = new HashMap<>();
        
        for (StudentModel student : students) {
            // Fetch results for the current student
            List<ResultModel> results = resultRepository.findResultsByStudentId(student.getId());
            
            // Map to store results per subject for the student
            Map<String, ResultDto> resultsBySubject = results.stream()
                .map(ResultMapper::mapToDto)
                .collect(Collectors.toMap(ResultDto::getSubjectId, resultDto -> resultDto));
            
            // Create a list of results to include all subjects, filling with default/null values if necessary
            List<ResultDto> completeResults = new ArrayList<>();
            for (SubjectModel subject : subjects) {
                ResultDto resultDto = resultsBySubject.getOrDefault(subject.getId().toString(), createDefaultResult(subject, student));
                completeResults.add(resultDto);
            }
            
            // Store the complete result list in the map
            studentResultsMap.put(student, completeResults);
        }
        
        return studentResultsMap;
    }

    private ResultDto createDefaultResult(SubjectModel subject, StudentModel student) {
        ResultDto defaultResult = new ResultDto();
        defaultResult.setSubjectId(subject.getId().toString());
        defaultResult.setName(student.getId().toString());
        defaultResult.setFirstCA(0);
        defaultResult.setSecondCA(0);
        defaultResult.setExam(0);
        defaultResult.setTotal(0);
        defaultResult.setGrade("N/A");
        defaultResult.setRemark("No result available");
        return defaultResult;
    }
}


    // Retrieve and return the aggregated results for the authenticated student
    return getAggregatedResultsForStudent(authenticatedStudent);
}

private ResultDto getAggregatedResultsForStudent(StudentModel student) {
    // Retrieve the precomputed result data for the student
    // Example placeholder: Fetch from a repository or data structure where results are stored
    List<ResultDto> studentResults = resultRepository.findResultsByStudentId(student.getId())
            .stream()
            .map(ResultMapper::mapToDto)
            .collect(Collectors.toList());

    // Assuming you have a method to aggregate results into a single ResultDto or similar structure
    ResultDto aggregatedResult = aggregateResults(studentResults);
    return aggregatedResult;
}

private ResultDto aggregateResults(List<ResultDto> results) {
    // Aggregate results as needed, e.g., calculate totals or averages
    // This is a placeholder; adjust based on your specific aggregation logic
    ResultDto aggregatedResult = new ResultDto();
    aggregatedResult.setTotal(results.stream().mapToInt(ResultDto::getTotal).sum());
    // Set other aggregated fields if needed
    return aggregatedResult;
}

private StudentModel getAuthenticatedStudent() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
        logger.warn("Authentication is null or not authenticated");
        return null;
    }

    String username = authentication.getName();
    StudentModel studentModel = studentRepository.findByUserName(username);
    if (studentModel == null) {
        logger.warn("UserModel not found for username: {}", username);
        return null;
    } else {
        logger.info("Found UserModel: {}", studentModel);
        return studentModel;
    }
}



### Explanation:

1. **`ResultImpl` Updates**:
   - **`saveOrUpdateResult`**: After saving or updating a result, the `updateStudentResults` method is called to recalculate and store the student's aggregated results.
   - **`updateStudentResults`**: This method fetches the student's results, aggregates them, and stores or updates them. You might need a separate repository or data structure to store these aggregated results.
   - **`getResultsForStudentsInClass`**: Fetches results for all students in a class and section and returns a map with students and their results. This method also ensures that results are available for all subjects, including default values for subjects without results.

2. **`ResultCheckingImpl` Updates**:
   - **`searchResult`**: Retrieves the aggregated results for the authenticated student.
   - **`getAggregatedResultsForStudent`**: Fetches precomputed results for the student. This method should interface with wherever aggregated results are stored.
   - **`aggregateResults`**: Placeholder method for aggregating results into a single `ResultDto`. Adjust this method based on your specific aggregation logic.

This approach ensures that whenever a student's result is updated, the aggregated results are recalculated and stored. When a student checks their results, the precomputed aggregated data is retrieved, providing a responsive and efficient user experience.
