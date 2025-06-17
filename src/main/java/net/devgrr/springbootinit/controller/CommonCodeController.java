package net.devgrr.springbootinit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.dto.CommonCodeCreateRequest;
import net.devgrr.springbootinit.dto.CommonCodeDto;
import net.devgrr.springbootinit.dto.CommonCodeUpdateRequest;
import net.devgrr.springbootinit.service.CommonCodeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/common-codes")
@RequiredArgsConstructor
@Tag(name = "Common Code Management", description = "Common code CRUD operations")
@SecurityRequirement(name = "Bearer Authentication")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @GetMapping
    @Operation(summary = "Get all codes", description = "Retrieve all common codes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Codes retrieved successfully")
    })
    public ResponseEntity<List<CommonCodeDto>> getAllCodes() {
        List<CommonCodeDto> codes = commonCodeService.getAllCodes();
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/page")
    @Operation(summary = "Get codes with pagination", description = "Retrieve common codes with pagination")
    public ResponseEntity<Page<CommonCodeDto>> getAllCodes(Pageable pageable) {
        Page<CommonCodeDto> codes = commonCodeService.getAllCodes(pageable);
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/group/{groupCode}")
    @Operation(summary = "Get codes by group code", description = "Retrieve common codes by group code")
    public ResponseEntity<List<CommonCodeDto>> getCodesByGroupCode(@PathVariable String groupCode) {
        List<CommonCodeDto> codes = commonCodeService.getCodesByGroupCode(groupCode);
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get code by ID", description = "Retrieve a specific common code by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Code found"),
            @ApiResponse(responseCode = "404", description = "Code not found")
    })
    public ResponseEntity<CommonCodeDto> getCodeById(@PathVariable Long id) {
        CommonCodeDto code = commonCodeService.getCodeById(id);
        return ResponseEntity.ok(code);
    }

    @GetMapping("/{groupCode}/{code}")
    @Operation(summary = "Get code by group code and code", description = "Retrieve a specific common code by group code and code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Code found"),
            @ApiResponse(responseCode = "404", description = "Code not found")
    })
    public ResponseEntity<CommonCodeDto> getCodeByGroupCodeAndCode(
            @PathVariable String groupCode, 
            @PathVariable String code) {
        CommonCodeDto commonCode = commonCodeService.getCodeByGroupCodeAndCode(groupCode, code);
        return ResponseEntity.ok(commonCode);
    }

    @GetMapping("/search")
    @Operation(summary = "Search codes", description = "Search common codes by keyword")
    public ResponseEntity<List<CommonCodeDto>> searchCodes(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        List<CommonCodeDto> codes = commonCodeService.searchCodes(keyword);
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/search/group/{groupCode}")
    @Operation(summary = "Search codes by group", description = "Search common codes by group code and keyword")
    public ResponseEntity<List<CommonCodeDto>> searchCodesByGroupCode(
            @PathVariable String groupCode,
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        List<CommonCodeDto> codes = commonCodeService.searchCodesByGroupCode(groupCode, keyword);
        return ResponseEntity.ok(codes);
    }

    @PostMapping
    @Operation(summary = "Create code", description = "Create a new common code (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Code created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or code already exists")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonCodeDto> createCode(@RequestBody CommonCodeCreateRequest request) {
        CommonCodeDto code = commonCodeService.createCode(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update code", description = "Update common code information (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Code updated successfully"),
            @ApiResponse(responseCode = "404", description = "Code not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonCodeDto> updateCode(
            @PathVariable Long id, 
            @RequestBody CommonCodeUpdateRequest request) {
        CommonCodeDto code = commonCodeService.updateCode(id, request);
        return ResponseEntity.ok(code);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete code", description = "Delete a common code (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Code deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Code not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
        commonCodeService.deleteCode(id);
        return ResponseEntity.noContent().build();
    }
}