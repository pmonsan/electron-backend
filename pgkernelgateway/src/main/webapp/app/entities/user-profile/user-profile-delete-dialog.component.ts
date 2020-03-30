import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';

@Component({
  selector: 'jhi-user-profile-delete-dialog',
  templateUrl: './user-profile-delete-dialog.component.html'
})
export class UserProfileDeleteDialogComponent {
  userProfile: IUserProfile;

  constructor(
    protected userProfileService: UserProfileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userProfileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userProfileListModification',
        content: 'Deleted an userProfile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-profile-delete-popup',
  template: ''
})
export class UserProfileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userProfile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserProfileDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userProfile = userProfile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/user-profile', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/user-profile', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
