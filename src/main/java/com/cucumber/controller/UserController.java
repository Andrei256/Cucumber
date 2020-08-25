package com.cucumber.controller;

import com.cucumber.model.Review;
import com.cucumber.model.Role;
import com.cucumber.model.User;
import com.cucumber.service.ReviewService;
import com.cucumber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ReviewService reviewService;

    @Autowired
    public UserController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("/registration")
    public String registrationView(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @Valid User user,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("errors", errors);
            return "registration";
        }
        if (userService.addUser(user)) {
            return "redirect:/login";
        }
        model.addAttribute("massage", "Имя пользователя занято");
        return "registration";
    }

    @GetMapping("/{sellerId}")
    public String showShopPage(
            @PathVariable("sellerId") long sellerId,
            Model model) {
        model.addAttribute("user", userService.get(sellerId));
        return "shop_page";
    }

    @GetMapping("/myPage")
    public String showUserPage(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        model.addAttribute("user", userService.get(user.getId()));
        return "my_page";
    }

    @GetMapping("/{userId}/edit")
    public String showUserEditPage(
            Model model,
            @PathVariable("userId") long userId
    ) {
        model.addAttribute("user", userService.get(userId));
        return "user_edit";
    }

    @PostMapping("/{userId}/edit")
    public String userEdit(
            @PathVariable("userId") long userId,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasFieldErrors("username") || bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("phoneNumber") || bindingResult.hasFieldErrors("text")) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("user", userService.get(userId));
            return "user_edit";
        }
        userService.editUser(userId, user);
        return "redirect:/user/myPage";
    }

    //USER

    @GetMapping("/{sellerId}/review/new")
    public String showNewReviewPage(
            Model model,
            @PathVariable("sellerId") long sellerId
    ) {
        model.addAttribute("review", new Review());
        model.addAttribute("sellerId", sellerId);
        return "user/new_review";
    }

    @PostMapping("/{sellerId}/review/add")
    public String addReview(
            @AuthenticationPrincipal User buyer,
            @PathVariable("sellerId") long sellerId,
            @Valid Review review,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("review", review);
            model.addAttribute("sellerId", sellerId);
            return "user/new_review";
        }
        reviewService.addReview(buyer, userService.get(sellerId), review);
        return "redirect:/user/" + sellerId;
    }


    @GetMapping("/{userId}/shopOpen")
    public String showShopOpenPage(
            Model model,
            @PathVariable("userId") long userId
    ) {
        model.addAttribute("user", userService.get(userId));
        return "user/shop_open";
    }

    @PostMapping("/{userId}/shopOpen")
    public String shopOpen(
            @PathVariable("userId") long userId,
            @ModelAttribute("user") User user

    ) {
        userService.editDataForShop(userId, user);
        return "redirect:/user/myPage";
    }

    //ADMIN

    @GetMapping
    public String showListUsersPage(
            Model model,
            @AuthenticationPrincipal User user) {
        model.addAttribute("listUsers", userService.getAllWhereNotEqual(user));
        return "admin/list_users";
    }

    @GetMapping("/{userId}/adminEdit")
    public String showUserPageForAdmin(
            Model model,
            @PathVariable("userId") long userId) {
        model.addAttribute("user", userService.get(userId));
        model.addAttribute("roles", Role.values());
        return "admin/user_page_for_admin";
    }

    @PostMapping("/{userId}/adminEdit")
    public String editUserRole(
            @PathVariable("userId") long id,
            @RequestParam("roles") Set<Role> roles
    ) {
        userService.editUserRole(id, roles);
        return "redirect:/user";
    }

    @PostMapping("/{userId}/active")
    public String editActive(
            @PathVariable("userId") long id,
            @RequestParam("active") boolean active
    ) {
        userService.editUserActive(id, active);
        return "redirect:/user";
    }

    @PostMapping("/search")
    public String search(
            @AuthenticationPrincipal User user,
            @RequestParam String keyword,
            Model model) {
        model.addAttribute("listUsers", userService.searchWithoutAnAuthorizedUser(user, keyword));
        return "admin/users_search";
    }

    @PostMapping("/{sellerId}/review/{reviewId}/delete")
    public String deleteReview(
            @PathVariable("reviewId") long reviewId,
            @PathVariable("sellerId") long sellerId
    ) {
        reviewService.delete(reviewId);
        return "redirect:/user/" + sellerId + "/adminEdit";
    }
}
