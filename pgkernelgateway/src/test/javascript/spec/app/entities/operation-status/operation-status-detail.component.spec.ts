/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { OperationStatusDetailComponent } from 'app/entities/operation-status/operation-status-detail.component';
import { OperationStatus } from 'app/shared/model/operation-status.model';

describe('Component Tests', () => {
  describe('OperationStatus Management Detail Component', () => {
    let comp: OperationStatusDetailComponent;
    let fixture: ComponentFixture<OperationStatusDetailComponent>;
    const route = ({ data: of({ operationStatus: new OperationStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [OperationStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OperationStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OperationStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.operationStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
