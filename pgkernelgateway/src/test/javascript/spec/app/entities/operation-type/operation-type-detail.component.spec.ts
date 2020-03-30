/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { OperationTypeDetailComponent } from 'app/entities/operation-type/operation-type-detail.component';
import { OperationType } from 'app/shared/model/operation-type.model';

describe('Component Tests', () => {
  describe('OperationType Management Detail Component', () => {
    let comp: OperationTypeDetailComponent;
    let fixture: ComponentFixture<OperationTypeDetailComponent>;
    const route = ({ data: of({ operationType: new OperationType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [OperationTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OperationTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OperationTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.operationType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
