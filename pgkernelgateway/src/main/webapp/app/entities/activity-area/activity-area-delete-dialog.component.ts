import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActivityArea } from 'app/shared/model/activity-area.model';
import { ActivityAreaService } from './activity-area.service';

@Component({
  selector: 'jhi-activity-area-delete-dialog',
  templateUrl: './activity-area-delete-dialog.component.html'
})
export class ActivityAreaDeleteDialogComponent {
  activityArea: IActivityArea;

  constructor(
    protected activityAreaService: ActivityAreaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.activityAreaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'activityAreaListModification',
        content: 'Deleted an activityArea'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-activity-area-delete-popup',
  template: ''
})
export class ActivityAreaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ activityArea }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ActivityAreaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.activityArea = activityArea;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/activity-area', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/activity-area', { outlets: { popup: null } }]);
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
