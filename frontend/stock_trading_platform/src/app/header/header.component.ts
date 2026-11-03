
import { CommonModule } from '@angular/common';
import { Component, inject, NO_ERRORS_SCHEMA } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import {PasswordModule} from 'primeng/password';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule,PasswordModule,ToastModule],
  providers:[MessageService],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

    formBuilder=inject(FormBuilder);
    toastModule=inject(ToastModule);
    userService=inject(UserService);
    messageService=inject(MessageService);

  //atleast 1 letter lower or upper,.*->any special character 0 or 1,[0-9]any number
  //^ start from here and $ end here

  registerForm:FormGroup=this.formBuilder.group({
    username:['',[Validators.required,Validators.minLength(5),Validators.maxLength(10),Validators.pattern("^[a-zA-Z]+[-_@]?[0-9]*$")]],
    password:['',[Validators.required,Validators.minLength(5),Validators.maxLength(10)]],
    confirmPassword:['',[Validators.required,Validators.minLength(5),Validators.maxLength(10)]],
    cashBalance:[0,[Validators.required,Validators.min(1000),Validators.max(10000)]]
  },{
    validators:this.passwordMatchValidator
  });

   //this validation error is for entire registerForm not only confirmPassword field
   passwordMatchValidator(control:AbstractControl):ValidationErrors | null{
    const password=control.get('password')?.value;
    const confirmPassword=control.get('confirmPassword')?.value;


    if(password!==confirmPassword){
       return {
         passwordMismatch:true
       };
    }

    return null;
   }


   onSubmit(){
    console.log(this.registerForm.value);
    this.userService.register(this.registerForm.value).subscribe({
      next:(res)=>{
          this.messageService.add({
            severity:'success',
            summary:"User Registered.",
            detail:"Registration Sucessfully Completed."
          })
      },
      error:(err)=>{
        this.messageService.add({
            severity:'error',
            summary:"Incomplete form fillup.",
            detail:"Please fill the important fields."
          })
      }
    })
   }
   get f(){
     return this.registerForm.controls;
   }

}
