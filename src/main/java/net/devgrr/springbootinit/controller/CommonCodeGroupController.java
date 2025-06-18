package net.devgrr.springbootinit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.dto.CommonCodeGroupCreateRequest;
import net.devgrr.springbootinit.dto.CommonCodeGroupDto;
import net.devgrr.springbootinit.service.CommonCodeGroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/common-code-groups")
@RequiredArgsConstructor
@Tag(name = "Common Code Group Management", description = "Common code group CRUD operations")
@SecurityRequirement(name = "Bearer Authentication")
public class CommonCodeGroupController {

    private final CommonCodeGroupService commonCodeGroupService;

    @GetMapping
    @Operation(summary = "Get all code groups", description = "Retrieve all common code groups")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Code groups retrieved successfully")
    })
    public ResponseEntity<List<CommonCodeGroupDto>> getAllGroups() {
        List<CommonCodeGroupDto> groups = commonCodeGroupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/page")
    @Operation(summary = "Get code groups with pagination", description = "Retrieve common code groups with pagination")
    public ResponseEntity<Page<CommonCodeGroupDto>> getAllGroups(Pageable pageable) {
        Page<CommonCodeGroupDto> groups = commonCodeGroupService.getAllGroups(pageable);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active code groups", description = "Retrieve only active common code groups")
    public ResponseEntity<List<CommonCodeGroupDto>> getActiveGroups() {
        List<CommonCodeGroupDto> groups = commonCodeGroupService.getActiveGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{groupCode}")
    @Operation(summary = "Get code group by group code", description = "Retrieve a specific common code group by group code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Code group found"),
            @ApiResponse(responseCode = "404", description = "Code group not found")
    })
    public ResponseEntity<CommonCodeGroupDto> getGroupByGroupCode(@PathVariable String groupCode) {
        CommonCodeGroupDto group = commonCodeGroupService.getGroupByGroupCode(groupCode);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/search")
    @Operation(summary = "Search code groups", description = "Search common code groups by keyword")
    public ResponseEntity<List<CommonCodeGroupDto>> searchGroups(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        List<CommonCodeGroupDto> groups = commonCodeGroupService.searchGroups(keyword);
        return ResponseEntity.ok(groups);
    }

    @PostMapping
    @Operation(summary = "Create code group", description = "Create a new common code group (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Code group created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or group already exists")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonCodeGroupDto> createGroup(@RequestBody CommonCodeGroupCreateRequest request) {
        CommonCodeGroupDto group = commonCodeGroupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @PutMapping("/{groupCode}")
    @Operation(summary = "Update code group", description = "Update common code group information (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Code group updated successfully"),
            @ApiResponse(responseCode = "404", description = "Code group not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonCodeGroupDto> updateGroup(
            @PathVariable String groupCode, 
            @RequestBody CommonCodeGroupCreateRequest request) {
        CommonCodeGroupDto group = commonCodeGroupService.updateGroup(groupCode, request);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/{groupCode}")
    @Operation(summary = "Delete code group", description = "Delete a common code group (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Code group deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Code group not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGroup(@PathVariable String groupCode) {
        commonCodeGroupService.deleteGroup(groupCode);
        return ResponseEntity.noContent().build();
    }
}