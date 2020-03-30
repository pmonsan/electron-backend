import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILoanInstalmentStatus } from 'app/shared/model/loan-instalment-status.model';
import { LoanInstalmentStatusService } from './loan-instalment-status.service';

@Component({
  selector: 'jhi-loan-instalment-status-delete-dialog',
  templateUrl: './loan-instalment-status-delete-dialog.component.html'
})
export class LoanInstalmentStatusDeleteDialogComponent {
  loanInstalmentStatus: ILoanInstalmentStatus;

  constructor(
    protected loanInstalmentStatusService: LoanInstalmentStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.loanInstalmentStatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'loanInstalmentStatusListModification',
        content: 'Deleted an loanInstalmentStatus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-loan-instalment-status-delete-popup',
  template: ''
})
export class LoanInstalmentStatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ loanInstalmentStatus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LoanInstalmentStatusDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.loanInstalmentStatus = loanInstalmentStatus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/loan-instalment-status', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/loan-instalment-status', { outlets: { popup: null } }]);
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
