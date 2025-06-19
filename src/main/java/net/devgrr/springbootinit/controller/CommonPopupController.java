package net.devgrr.springbootinit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.dto.CommonPopupCreateRequest;
import net.devgrr.springbootinit.dto.CommonPopupDto;
import net.devgrr.springbootinit.entity.PopupType;
import net.devgrr.springbootinit.service.CommonPopupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/popups")
@RequiredArgsConstructor
@Tag(name = "Common Popup Management", description = "Common popup CRUD operations")
@SecurityRequirement(name = "Bearer Authentication")
public class CommonPopupController {

    private final CommonPopupService commonPopupService;

    @GetMapping
    @Operation(summary = "Get all popups", description = "Retrieve all active popups")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Popups retrieved successfully")
    })
    public ResponseEntity<List<CommonPopupDto>> getAllPopups() {
        List<CommonPopupDto> popups = commonPopupService.getAllPopups();
        return ResponseEntity.ok(popups);
    }

    @GetMapping("/page")
    @Operation(summary = "Get popups with pagination", description = "Retrieve popups with pagination")
    public ResponseEntity<Page<CommonPopupDto>> getAllPopups(Pageable pageable) {
        Page<CommonPopupDto> popups = commonPopupService.getAllPopups(pageable);
        return ResponseEntity.ok(popups);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active popups", description = "Retrieve currently displayable popups")
    public ResponseEntity<List<CommonPopupDto>> getActivePopups() {
        List<CommonPopupDto> popups = commonPopupService.getActivePopups();
        return ResponseEntity.ok(popups);
    }

    @GetMapping("/active/page")
    @Operation(summary = "Get active popups for specific page", description = "Retrieve active popups for specific target page")
    public ResponseEntity<List<CommonPopupDto>> getActivePopupsForPage(
            @Parameter(description = "Target page identifier") @RequestParam(required = false) String targetPage) {
        List<CommonPopupDto> popups = commonPopupService.getActivePopupsForPage(targetPage);
        return ResponseEntity.ok(popups);
    }

    @GetMapping("/type/{popupType}")
    @Operation(summary = "Get popups by type", description = "Retrieve popups by popup type")
    public ResponseEntity<List<CommonPopupDto>> getPopupsByType(@PathVariable PopupType popupType) {
        List<CommonPopupDto> popups = commonPopupService.getPopupsByType(popupType);
        return ResponseEntity.ok(popups);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get popup by ID", description = "Retrieve a specific popup by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Popup found"),
            @ApiResponse(responseCode = "404", description = "Popup not found")
    })
    public ResponseEntity<CommonPopupDto> getPopupById(@PathVariable Long id) {
        CommonPopupDto popup = commonPopupService.getPopupById(id);
        return ResponseEntity.ok(popup);
    }

    @GetMapping("/search")
    @Operation(summary = "Search popups", description = "Search popups by keyword in title or content")
    public ResponseEntity<List<CommonPopupDto>> searchPopups(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        List<CommonPopupDto> popups = commonPopupService.searchPopups(keyword);
        return ResponseEntity.ok(popups);
    }

    @GetMapping("/count/active")
    @Operation(summary = "Get active popup count", description = "Get count of currently active popups")
    public ResponseEntity<Long> getActivePopupCount() {
        long count = commonPopupService.getActivePopupCount();
        return ResponseEntity.ok(count);
    }

    @PostMapping
    @Operation(summary = "Create popup", description = "Create a new popup (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Popup created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonPopupDto> createPopup(@Valid @RequestBody CommonPopupCreateRequest request) {
        CommonPopupDto popup = commonPopupService.createPopup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(popup);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update popup", description = "Update popup information (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Popup updated successfully"),
            @ApiResponse(responseCode = "404", description = "Popup not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonPopupDto> updatePopup(
            @PathVariable Long id, 
            @Valid @RequestBody CommonPopupCreateRequest request) {
        CommonPopupDto popup = commonPopupService.updatePopup(id, request);
        return ResponseEntity.ok(popup);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete popup", description = "Delete a popup (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Popup deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Popup not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePopup(@PathVariable Long id) {
        commonPopupService.deletePopup(id);
        return ResponseEntity.noContent().build();
    }
}