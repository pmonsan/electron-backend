/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgChannelAuthorizedDetailComponent } from 'app/entities/pg-channel-authorized/pg-channel-authorized-detail.component';
import { PgChannelAuthorized } from 'app/shared/model/pg-channel-authorized.model';

describe('Component Tests', () => {
  describe('PgChannelAuthorized Management Detail Component', () => {
    let comp: PgChannelAuthorizedDetailComponent;
    let fixture: ComponentFixture<PgChannelAuthorizedDetailComponent>;
    const route = ({ data: of({ pgChannelAuthorized: new PgChannelAuthorized(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgChannelAuthorizedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgChannelAuthorizedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgChannelAuthorizedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgChannelAuthorized).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
