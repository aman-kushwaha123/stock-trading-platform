import { CommonModule } from '@angular/common';
import { Component, inject, NO_ERRORS_SCHEMA } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { HotToastService } from '@ngneat/hot-toast';
import { Router, RouterLink } from '@angular/router';


@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {
  formBuilder = inject(FormBuilder);
  router=inject(Router)
  toastService = inject(HotToastService)
  userService = inject(UserService);
  showPassword = false;
  showConfirmPassword = false;

  ngOnInit() {

  }


  //atleast 1 letter lower or upper,.*->any special character 0 or 1,[0-9]any number
  //^ start from here and $ end here

  loginForm: FormGroup = this.formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(10), Validators.pattern("^[a-zA-Z]+[-_@]?[0-9]*$")]],
    password: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(10)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(10)]],
  }, {
    validators: this.passwordMatchValidator
  });

  //this validation error is for entire registerForm not only confirmPassword field
  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;


    if (password !== confirmPassword) {
      return {
        passwordMismatch: true
      };
    }

    return null;
  }


  onSubmit() {

    /*
Option 1: Pipe observe() Before .subscribe() (Recommended)

Move this.toastService.observe(...) inside .pipe() on your HTTP call so it tracks the request from start to finish.*/

    console.log(this.loginForm.value);
    if (this.loginForm.invalid) return;

    this.userService.login(this.loginForm.value).pipe(
      this.toastService.observe({
        loading: 'Logging...',
        success: 'Data synced successfully!',
        error: 'Login failed.'
      })
    ).subscribe({
      next: (res:any) => {
        localStorage.setItem("token", res.token)
        localStorage.setItem("user", JSON.stringify(res.user))
        this.toastService.success('Successfully Logged In')
        this.router.navigateByUrl("/");
      },
      error: (err) => {
        console.error('Error:', err);
        this.toastService.error('error', err)
      }
    });
  }


  get f() {
    return this.loginForm.controls;
  }
}
