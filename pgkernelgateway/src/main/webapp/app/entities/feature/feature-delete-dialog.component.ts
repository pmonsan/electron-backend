import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFeature } from 'app/shared/model/feature.model';
import { FeatureService } from './feature.service';

@Component({
  selector: 'jhi-feature-delete-dialog',
  templateUrl: './feature-delete-dialog.component.html'
})
export class FeatureDeleteDialogComponent {
  feature: IFeature;

  constructor(protected featureService: FeatureService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.featureService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'featureListModification',
        content: 'Deleted an feature'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-feature-delete-popup',
  template: ''
})
export class FeatureDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ feature }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FeatureDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.feature = feature;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/feature', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/feature', { outlets: { popup: null } }]);
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
