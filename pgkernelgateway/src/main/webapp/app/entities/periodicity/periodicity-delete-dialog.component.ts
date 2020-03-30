import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPeriodicity } from 'app/shared/model/periodicity.model';
import { PeriodicityService } from './periodicity.service';

@Component({
  selector: 'jhi-periodicity-delete-dialog',
  templateUrl: './periodicity-delete-dialog.component.html'
})
export class PeriodicityDeleteDialogComponent {
  periodicity: IPeriodicity;

  constructor(
    protected periodicityService: PeriodicityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.periodicityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'periodicityListModification',
        content: 'Deleted an periodicity'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-periodicity-delete-popup',
  template: ''
})
export class PeriodicityDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ periodicity }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PeriodicityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.periodicity = periodicity;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/periodicity', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/periodicity', { outlets: { popup: null } }]);
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
