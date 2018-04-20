import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { MatSnackBar } from '@angular/material/snack-bar';

import { TdLoadingService } from '@covalent/core/loading';
import { TdDialogService } from '@covalent/core/dialogs';
import { TdMediaService } from '@covalent/core/media';

import { Observable } from 'rxjs/Observable';

import {} from ''
import { of } from 'rxjs';
import {User} from '../../models/user.model';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'qs-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
})
export class UsersComponent implements OnInit {

  // users: User[];
  // filteredUsers: IUser[];
  users: User[];

  constructor(private _titleService: Title,
              private _loadingService: TdLoadingService,
              private _dialogService: TdDialogService,
              private _snackBarService: MatSnackBar,
              private _userService: UserService,
              private _changeDetectorRef: ChangeDetectorRef,
              public media: TdMediaService) {
  }

  ngOnInit(): void {
    this._titleService.setTitle('Covalent Users');
    // this.load();
    this._userService.getUsers().subscribe(users => this.users = users);
  }

  // filterUsers(displayName: string = ''): void {
  //   this.filteredUsers = this.users.filter((user: IUser) => {
  //     return user.displayName.toLowerCase().indexOf(displayName.toLowerCase()) > -1;
  //   });
  // }

  // async load(): Promise<void> {
  //   try {
  //     this._loadingService.register('users.list');
  //     this.users = await this._userService.query().toPromise();
  //   } catch (error) {
  //     this.users = await this._userService.staticQuery().toPromise();
  //   } finally {
  //     this.filteredUsers = Object.assign([], this.users);
  //     this._loadingService.resolve('users.list');
  //   }
  // }

  // delete(id: string): void {
  //   this._dialogService
  //     .openConfirm({message: 'Are you sure you want to delete this user?'})
  //     .afterClosed().toPromise().then((confirm: boolean) => {
  //       if (confirm) {
  //         this._delete(id);
  //       }
  //     });
  // }
  //
  // private async _delete(id: string): Promise<void> {
  //   try {
  //     this._loadingService.register('users.list');
  //     await this._userService.delete(id).toPromise();
  //     this.users = this.users.filter((user: IUser) => {
  //       return user.id !== id;
  //     });
  //     this.filteredUsers = this.filteredUsers.filter((user: IUser) => {
  //       return user.id !== id;
  //     });
  //     this._snackBarService.open('User Deleted', 'Ok');
  //   } catch (error) {
  //     this._dialogService.openAlert({message: 'There was an error trying to delete the user'});
  //   } finally {
  //     this._loadingService.resolve('users.list');
  //   }
  // }

}
