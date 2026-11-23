
import { CommonModule } from '@angular/common';
import { Component, inject, NO_ERRORS_SCHEMA } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { RouterLink } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';

@Component({
  selector: 'app-register-page',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.css'
})
export class RegisterPageComponent {

  formBuilder = inject(FormBuilder);
  showPassword = false;
  toastService = inject(HotToastService)
  showConfirmPassword = false;
  userService = inject(UserService);


  //atleast 1 letter lower or upper,.*->any special character 0 or 1,[0-9]any number
  //^ start from here and $ end here

  registerForm: FormGroup = this.formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(10), Validators.pattern("^[a-zA-Z]+[-_@]?[0-9]*$")]],
    password: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(10)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(10)]],
    cashBalance: [0, [Validators.required, Validators.min(1000), Validators.max(10000)]]
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
    console.log(this.registerForm.value);
    this.userService.register(this.registerForm.value)
    this.userService.login(this.registerForm.value).pipe(
      this.toastService.observe({
        loading: 'Uploading changes...',
        success: 'Data synced successfully!',
        error: 'Upload failed.'
      })
    ).subscribe({
      next: (res) => {
        console.log('Login Response:', res);
      },
      error: (err) => {
        console.error('Error:', err);
        this.toastService.error('error', err)
      }
    })
  }
  get f() {
    return this.registerForm.controls;
  }
}
